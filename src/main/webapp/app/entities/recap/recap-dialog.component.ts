import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Recap } from './recap.model';
import { RecapPopupService } from './recap-popup.service';
import { RecapService } from './recap.service';

@Component({
    selector: 'jhi-recap-dialog',
    templateUrl: './recap-dialog.component.html'
})
export class RecapDialogComponent implements OnInit {

    recap: Recap;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private recapService: RecapService,
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
        if (this.recap.id !== undefined) {
            this.subscribeToSaveResponse(
                this.recapService.update(this.recap));
        } else {
            this.subscribeToSaveResponse(
                this.recapService.create(this.recap));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Recap>>) {
        result.subscribe((res: HttpResponse<Recap>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Recap) {
        this.eventManager.broadcast({ name: 'recapListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-recap-popup',
    template: ''
})
export class RecapPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recapPopupService: RecapPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.recapPopupService
                    .open(RecapDialogComponent as Component, params['id']);
            } else {
                this.recapPopupService
                    .open(RecapDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
