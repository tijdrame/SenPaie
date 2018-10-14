import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeAbsence } from './type-absence.model';
import { TypeAbsencePopupService } from './type-absence-popup.service';
import { TypeAbsenceService } from './type-absence.service';

@Component({
    selector: 'jhi-type-absence-dialog',
    templateUrl: './type-absence-dialog.component.html'
})
export class TypeAbsenceDialogComponent implements OnInit {

    typeAbsence: TypeAbsence;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeAbsenceService: TypeAbsenceService,
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
        if (this.typeAbsence.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeAbsenceService.update(this.typeAbsence));
        } else {
            this.subscribeToSaveResponse(
                this.typeAbsenceService.create(this.typeAbsence));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeAbsence>>) {
        result.subscribe((res: HttpResponse<TypeAbsence>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeAbsence) {
        this.eventManager.broadcast({ name: 'typeAbsenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-absence-popup',
    template: ''
})
export class TypeAbsencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeAbsencePopupService: TypeAbsencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeAbsencePopupService
                    .open(TypeAbsenceDialogComponent as Component, params['id']);
            } else {
                this.typeAbsencePopupService
                    .open(TypeAbsenceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
