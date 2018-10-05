import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeRelation } from './type-relation.model';
import { TypeRelationPopupService } from './type-relation-popup.service';
import { TypeRelationService } from './type-relation.service';

@Component({
    selector: 'jhi-type-relation-delete-dialog',
    templateUrl: './type-relation-delete-dialog.component.html'
})
export class TypeRelationDeleteDialogComponent {

    typeRelation: TypeRelation;

    constructor(
        private typeRelationService: TypeRelationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeRelationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeRelationListModification',
                content: 'Deleted an typeRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-relation-delete-popup',
    template: ''
})
export class TypeRelationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeRelationPopupService: TypeRelationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeRelationPopupService
                .open(TypeRelationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
