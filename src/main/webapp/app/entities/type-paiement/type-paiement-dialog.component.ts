import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypePaiement } from './type-paiement.model';
import { TypePaiementPopupService } from './type-paiement-popup.service';
import { TypePaiementService } from './type-paiement.service';

@Component({
    selector: 'jhi-type-paiement-dialog',
    templateUrl: './type-paiement-dialog.component.html'
})
export class TypePaiementDialogComponent implements OnInit {

    typePaiement: TypePaiement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typePaiementService: TypePaiementService,
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
        if (this.typePaiement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typePaiementService.update(this.typePaiement));
        } else {
            this.subscribeToSaveResponse(
                this.typePaiementService.create(this.typePaiement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypePaiement>>) {
        result.subscribe((res: HttpResponse<TypePaiement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypePaiement) {
        this.eventManager.broadcast({ name: 'typePaiementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-paiement-popup',
    template: ''
})
export class TypePaiementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typePaiementPopupService: TypePaiementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typePaiementPopupService
                    .open(TypePaiementDialogComponent as Component, params['id']);
            } else {
                this.typePaiementPopupService
                    .open(TypePaiementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
