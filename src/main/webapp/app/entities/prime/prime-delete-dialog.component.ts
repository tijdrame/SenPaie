import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Prime } from './prime.model';
import { PrimePopupService } from './prime-popup.service';
import { PrimeService } from './prime.service';

@Component({
    selector: 'jhi-prime-delete-dialog',
    templateUrl: './prime-delete-dialog.component.html'
})
export class PrimeDeleteDialogComponent {

    prime: Prime;

    constructor(
        private primeService: PrimeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.primeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'primeListModification',
                content: 'Deleted an prime'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prime-delete-popup',
    template: ''
})
export class PrimeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private primePopupService: PrimePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.primePopupService
                .open(PrimeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
