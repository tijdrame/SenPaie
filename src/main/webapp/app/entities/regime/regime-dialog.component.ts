import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Regime } from './regime.model';
import { RegimePopupService } from './regime-popup.service';
import { RegimeService } from './regime.service';

@Component({
    selector: 'jhi-regime-dialog',
    templateUrl: './regime-dialog.component.html'
})
export class RegimeDialogComponent implements OnInit {

    regime: Regime;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private regimeService: RegimeService,
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
        if (this.regime.id !== undefined) {
            this.subscribeToSaveResponse(
                this.regimeService.update(this.regime));
        } else {
            this.subscribeToSaveResponse(
                this.regimeService.create(this.regime));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Regime>>) {
        result.subscribe((res: HttpResponse<Regime>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Regime) {
        this.eventManager.broadcast({ name: 'regimeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-regime-popup',
    template: ''
})
export class RegimePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regimePopupService: RegimePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.regimePopupService
                    .open(RegimeDialogComponent as Component, params['id']);
            } else {
                this.regimePopupService
                    .open(RegimeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
