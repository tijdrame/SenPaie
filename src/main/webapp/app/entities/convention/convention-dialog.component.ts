import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Convention } from './convention.model';
import { ConventionPopupService } from './convention-popup.service';
import { ConventionService } from './convention.service';

@Component({
    selector: 'jhi-convention-dialog',
    templateUrl: './convention-dialog.component.html'
})
export class ConventionDialogComponent implements OnInit {

    convention: Convention;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private conventionService: ConventionService,
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
        if (this.convention.id !== undefined) {
            this.subscribeToSaveResponse(
                this.conventionService.update(this.convention));
        } else {
            this.subscribeToSaveResponse(
                this.conventionService.create(this.convention));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Convention>>) {
        result.subscribe((res: HttpResponse<Convention>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Convention) {
        this.eventManager.broadcast({ name: 'conventionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-convention-popup',
    template: ''
})
export class ConventionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conventionPopupService: ConventionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.conventionPopupService
                    .open(ConventionDialogComponent as Component, params['id']);
            } else {
                this.conventionPopupService
                    .open(ConventionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
