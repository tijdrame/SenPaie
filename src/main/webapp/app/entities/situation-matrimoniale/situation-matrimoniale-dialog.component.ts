import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SituationMatrimoniale } from './situation-matrimoniale.model';
import { SituationMatrimonialePopupService } from './situation-matrimoniale-popup.service';
import { SituationMatrimonialeService } from './situation-matrimoniale.service';

@Component({
    selector: 'jhi-situation-matrimoniale-dialog',
    templateUrl: './situation-matrimoniale-dialog.component.html'
})
export class SituationMatrimonialeDialogComponent implements OnInit {

    situationMatrimoniale: SituationMatrimoniale;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private situationMatrimonialeService: SituationMatrimonialeService,
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
        if (this.situationMatrimoniale.id !== undefined) {
            this.subscribeToSaveResponse(
                this.situationMatrimonialeService.update(this.situationMatrimoniale));
        } else {
            this.subscribeToSaveResponse(
                this.situationMatrimonialeService.create(this.situationMatrimoniale));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SituationMatrimoniale>>) {
        result.subscribe((res: HttpResponse<SituationMatrimoniale>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SituationMatrimoniale) {
        this.eventManager.broadcast({ name: 'situationMatrimonialeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-situation-matrimoniale-popup',
    template: ''
})
export class SituationMatrimonialePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private situationMatrimonialePopupService: SituationMatrimonialePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.situationMatrimonialePopupService
                    .open(SituationMatrimonialeDialogComponent as Component, params['id']);
            } else {
                this.situationMatrimonialePopupService
                    .open(SituationMatrimonialeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
