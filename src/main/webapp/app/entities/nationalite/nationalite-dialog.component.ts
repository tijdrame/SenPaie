import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Nationalite } from './nationalite.model';
import { NationalitePopupService } from './nationalite-popup.service';
import { NationaliteService } from './nationalite.service';

@Component({
    selector: 'jhi-nationalite-dialog',
    templateUrl: './nationalite-dialog.component.html'
})
export class NationaliteDialogComponent implements OnInit {

    nationalite: Nationalite;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private nationaliteService: NationaliteService,
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
        if (this.nationalite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nationaliteService.update(this.nationalite));
        } else {
            this.subscribeToSaveResponse(
                this.nationaliteService.create(this.nationalite));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Nationalite>>) {
        result.subscribe((res: HttpResponse<Nationalite>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Nationalite) {
        this.eventManager.broadcast({ name: 'nationaliteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-nationalite-popup',
    template: ''
})
export class NationalitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nationalitePopupService: NationalitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nationalitePopupService
                    .open(NationaliteDialogComponent as Component, params['id']);
            } else {
                this.nationalitePopupService
                    .open(NationaliteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
