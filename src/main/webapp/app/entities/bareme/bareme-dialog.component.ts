import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bareme } from './bareme.model';
import { BaremePopupService } from './bareme-popup.service';
import { BaremeService } from './bareme.service';

@Component({
    selector: 'jhi-bareme-dialog',
    templateUrl: './bareme-dialog.component.html'
})
export class BaremeDialogComponent implements OnInit {

    bareme: Bareme;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private baremeService: BaremeService,
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
        if (this.bareme.id !== undefined) {
            this.subscribeToSaveResponse(
                this.baremeService.update(this.bareme));
        } else {
            this.subscribeToSaveResponse(
                this.baremeService.create(this.bareme));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bareme>>) {
        result.subscribe((res: HttpResponse<Bareme>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bareme) {
        this.eventManager.broadcast({ name: 'baremeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-bareme-popup',
    template: ''
})
export class BaremePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private baremePopupService: BaremePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.baremePopupService
                    .open(BaremeDialogComponent as Component, params['id']);
            } else {
                this.baremePopupService
                    .open(BaremeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
