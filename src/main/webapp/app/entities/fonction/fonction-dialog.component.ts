import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Fonction } from './fonction.model';
import { FonctionPopupService } from './fonction-popup.service';
import { FonctionService } from './fonction.service';

@Component({
    selector: 'jhi-fonction-dialog',
    templateUrl: './fonction-dialog.component.html'
})
export class FonctionDialogComponent implements OnInit {

    fonction: Fonction;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private fonctionService: FonctionService,
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
        if (this.fonction.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fonctionService.update(this.fonction));
        } else {
            this.subscribeToSaveResponse(
                this.fonctionService.create(this.fonction));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Fonction>>) {
        result.subscribe((res: HttpResponse<Fonction>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Fonction) {
        this.eventManager.broadcast({ name: 'fonctionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-fonction-popup',
    template: ''
})
export class FonctionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fonctionPopupService: FonctionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fonctionPopupService
                    .open(FonctionDialogComponent as Component, params['id']);
            } else {
                this.fonctionPopupService
                    .open(FonctionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
