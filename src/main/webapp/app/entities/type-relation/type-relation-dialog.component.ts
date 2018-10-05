import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeRelation } from './type-relation.model';
import { TypeRelationPopupService } from './type-relation-popup.service';
import { TypeRelationService } from './type-relation.service';

@Component({
    selector: 'jhi-type-relation-dialog',
    templateUrl: './type-relation-dialog.component.html'
})
export class TypeRelationDialogComponent implements OnInit {

    typeRelation: TypeRelation;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeRelationService: TypeRelationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.typeRelation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeRelationService.update(this.typeRelation));
        } else {
            this.subscribeToSaveResponse(
                this.typeRelationService.create(this.typeRelation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeRelation>>) {
        result.subscribe((res: HttpResponse<TypeRelation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeRelation) {
        this.eventManager.broadcast({ name: 'typeRelationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-relation-popup',
    template: ''
})
export class TypeRelationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeRelationPopupService: TypeRelationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeRelationPopupService
                    .open(TypeRelationDialogComponent as Component, params['id']);
            } else {
                this.typeRelationPopupService
                    .open(TypeRelationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
