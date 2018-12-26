import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MoisConcerne } from './mois-concerne.model';
import { MoisConcernePopupService } from './mois-concerne-popup.service';
import { MoisConcerneService } from './mois-concerne.service';

@Component({
    selector: 'jhi-mois-concerne-dialog',
    templateUrl: './mois-concerne-dialog.component.html'
})
export class MoisConcerneDialogComponent implements OnInit {

    moisConcerne: MoisConcerne;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private moisConcerneService: MoisConcerneService,
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
        if (this.moisConcerne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.moisConcerneService.update(this.moisConcerne));
        } else {
            this.subscribeToSaveResponse(
                this.moisConcerneService.create(this.moisConcerne));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MoisConcerne>>) {
        result.subscribe((res: HttpResponse<MoisConcerne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MoisConcerne) {
        this.eventManager.broadcast({ name: 'moisConcerneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mois-concerne-popup',
    template: ''
})
export class MoisConcernePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moisConcernePopupService: MoisConcernePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.moisConcernePopupService
                    .open(MoisConcerneDialogComponent as Component, params['id']);
            } else {
                this.moisConcernePopupService
                    .open(MoisConcerneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
