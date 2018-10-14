import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Motif } from './motif.model';
import { MotifPopupService } from './motif-popup.service';
import { MotifService } from './motif.service';

@Component({
    selector: 'jhi-motif-dialog',
    templateUrl: './motif-dialog.component.html'
})
export class MotifDialogComponent implements OnInit {

    motif: Motif;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private motifService: MotifService,
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
        if (this.motif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.motifService.update(this.motif));
        } else {
            this.subscribeToSaveResponse(
                this.motifService.create(this.motif));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Motif>>) {
        result.subscribe((res: HttpResponse<Motif>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Motif) {
        this.eventManager.broadcast({ name: 'motifListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-motif-popup',
    template: ''
})
export class MotifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private motifPopupService: MotifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.motifPopupService
                    .open(MotifDialogComponent as Component, params['id']);
            } else {
                this.motifPopupService
                    .open(MotifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
