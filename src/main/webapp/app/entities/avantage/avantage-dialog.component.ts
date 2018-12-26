import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Avantage } from './avantage.model';
import { AvantagePopupService } from './avantage-popup.service';
import { AvantageService } from './avantage.service';

@Component({
    selector: 'jhi-avantage-dialog',
    templateUrl: './avantage-dialog.component.html'
})
export class AvantageDialogComponent implements OnInit {

    avantage: Avantage;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private avantageService: AvantageService,
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
        if (this.avantage.id !== undefined) {
            this.subscribeToSaveResponse(
                this.avantageService.update(this.avantage));
        } else {
            this.subscribeToSaveResponse(
                this.avantageService.create(this.avantage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Avantage>>) {
        result.subscribe((res: HttpResponse<Avantage>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Avantage) {
        this.eventManager.broadcast({ name: 'avantageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-avantage-popup',
    template: ''
})
export class AvantagePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avantagePopupService: AvantagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.avantagePopupService
                    .open(AvantageDialogComponent as Component, params['id']);
            } else {
                this.avantagePopupService
                    .open(AvantageDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
