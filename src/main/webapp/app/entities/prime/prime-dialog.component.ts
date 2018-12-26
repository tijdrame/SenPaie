import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Prime } from './prime.model';
import { PrimePopupService } from './prime-popup.service';
import { PrimeService } from './prime.service';

@Component({
    selector: 'jhi-prime-dialog',
    templateUrl: './prime-dialog.component.html'
})
export class PrimeDialogComponent implements OnInit {

    prime: Prime;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private primeService: PrimeService,
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
        if (this.prime.id !== undefined) {
            this.subscribeToSaveResponse(
                this.primeService.update(this.prime));
        } else {
            this.subscribeToSaveResponse(
                this.primeService.create(this.prime));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Prime>>) {
        result.subscribe((res: HttpResponse<Prime>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Prime) {
        this.eventManager.broadcast({ name: 'primeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-prime-popup',
    template: ''
})
export class PrimePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private primePopupService: PrimePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.primePopupService
                    .open(PrimeDialogComponent as Component, params['id']);
            } else {
                this.primePopupService
                    .open(PrimeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
