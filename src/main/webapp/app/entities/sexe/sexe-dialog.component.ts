import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Sexe } from './sexe.model';
import { SexePopupService } from './sexe-popup.service';
import { SexeService } from './sexe.service';

@Component({
    selector: 'jhi-sexe-dialog',
    templateUrl: './sexe-dialog.component.html'
})
export class SexeDialogComponent implements OnInit {

    sexe: Sexe;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private sexeService: SexeService,
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
        if (this.sexe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sexeService.update(this.sexe));
        } else {
            this.subscribeToSaveResponse(
                this.sexeService.create(this.sexe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Sexe>>) {
        result.subscribe((res: HttpResponse<Sexe>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Sexe) {
        this.eventManager.broadcast({ name: 'sexeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-sexe-popup',
    template: ''
})
export class SexePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sexePopupService: SexePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sexePopupService
                    .open(SexeDialogComponent as Component, params['id']);
            } else {
                this.sexePopupService
                    .open(SexeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
