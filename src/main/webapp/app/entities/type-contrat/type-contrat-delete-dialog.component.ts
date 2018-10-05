import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeContrat } from './type-contrat.model';
import { TypeContratPopupService } from './type-contrat-popup.service';
import { TypeContratService } from './type-contrat.service';

@Component({
    selector: 'jhi-type-contrat-delete-dialog',
    templateUrl: './type-contrat-delete-dialog.component.html'
})
export class TypeContratDeleteDialogComponent {

    typeContrat: TypeContrat;

    constructor(
        private typeContratService: TypeContratService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeContratService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeContratListModification',
                content: 'Deleted an typeContrat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-contrat-delete-popup',
    template: ''
})
export class TypeContratDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeContratPopupService: TypeContratPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeContratPopupService
                .open(TypeContratDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
