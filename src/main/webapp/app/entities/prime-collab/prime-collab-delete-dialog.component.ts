import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PrimeCollab } from './prime-collab.model';
import { PrimeCollabPopupService } from './prime-collab-popup.service';
import { PrimeCollabService } from './prime-collab.service';

@Component({
    selector: 'jhi-prime-collab-delete-dialog',
    templateUrl: './prime-collab-delete-dialog.component.html'
})
export class PrimeCollabDeleteDialogComponent {

    primeCollab: PrimeCollab;

    constructor(
        private primeCollabService: PrimeCollabService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.primeCollabService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'primeCollabListModification',
                content: 'Deleted an primeCollab'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prime-collab-delete-popup',
    template: ''
})
export class PrimeCollabDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private primeCollabPopupService: PrimeCollabPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.primeCollabPopupService
                .open(PrimeCollabDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
