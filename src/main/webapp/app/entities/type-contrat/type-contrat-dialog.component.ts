import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeContrat } from './type-contrat.model';
import { TypeContratPopupService } from './type-contrat-popup.service';
import { TypeContratService } from './type-contrat.service';

@Component({
    selector: 'jhi-type-contrat-dialog',
    templateUrl: './type-contrat-dialog.component.html'
})
export class TypeContratDialogComponent implements OnInit {

    typeContrat: TypeContrat;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeContratService: TypeContratService,
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
        if (this.typeContrat.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeContratService.update(this.typeContrat));
        } else {
            this.subscribeToSaveResponse(
                this.typeContratService.create(this.typeContrat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeContrat>>) {
        result.subscribe((res: HttpResponse<TypeContrat>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeContrat) {
        this.eventManager.broadcast({ name: 'typeContratListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-contrat-popup',
    template: ''
})
export class TypeContratPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeContratPopupService: TypeContratPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeContratPopupService
                    .open(TypeContratDialogComponent as Component, params['id']);
            } else {
                this.typeContratPopupService
                    .open(TypeContratDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
