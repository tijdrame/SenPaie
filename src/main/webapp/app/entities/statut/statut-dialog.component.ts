import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Statut } from './statut.model';
import { StatutPopupService } from './statut-popup.service';
import { StatutService } from './statut.service';

@Component({
    selector: 'jhi-statut-dialog',
    templateUrl: './statut-dialog.component.html'
})
export class StatutDialogComponent implements OnInit {

    statut: Statut;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private statutService: StatutService,
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
        if (this.statut.id !== undefined) {
            this.subscribeToSaveResponse(
                this.statutService.update(this.statut));
        } else {
            this.subscribeToSaveResponse(
                this.statutService.create(this.statut));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Statut>>) {
        result.subscribe((res: HttpResponse<Statut>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Statut) {
        this.eventManager.broadcast({ name: 'statutListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-statut-popup',
    template: ''
})
export class StatutPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statutPopupService: StatutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.statutPopupService
                    .open(StatutDialogComponent as Component, params['id']);
            } else {
                this.statutPopupService
                    .open(StatutDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
