import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sexe } from './sexe.model';
import { SexePopupService } from './sexe-popup.service';
import { SexeService } from './sexe.service';

@Component({
    selector: 'jhi-sexe-delete-dialog',
    templateUrl: './sexe-delete-dialog.component.html'
})
export class SexeDeleteDialogComponent {

    sexe: Sexe;

    constructor(
        private sexeService: SexeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sexeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sexeListModification',
                content: 'Deleted an sexe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sexe-delete-popup',
    template: ''
})
export class SexeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sexePopupService: SexePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sexePopupService
                .open(SexeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
