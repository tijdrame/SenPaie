import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PrimeCollab } from './prime-collab.model';
import { PrimeCollabPopupService } from './prime-collab-popup.service';
import { PrimeCollabService } from './prime-collab.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { Prime, PrimeService } from '../prime';

@Component({
    selector: 'jhi-prime-collab-dialog',
    templateUrl: './prime-collab-dialog.component.html'
})
export class PrimeCollabDialogComponent implements OnInit {

    primeCollab: PrimeCollab;
    isSaving: boolean;

    collaborateurs: Collaborateur[];

    primes: Prime[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private primeCollabService: PrimeCollabService,
        private collaborateurService: CollaborateurService,
        private primeService: PrimeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.primeService.query()
            .subscribe((res: HttpResponse<Prime[]>) => { this.primes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.primeCollab.id !== undefined) {
            this.subscribeToSaveResponse(
                this.primeCollabService.update(this.primeCollab));
        } else {
            this.subscribeToSaveResponse(
                this.primeCollabService.create(this.primeCollab));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PrimeCollab>>) {
        result.subscribe((res: HttpResponse<PrimeCollab>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PrimeCollab) {
        this.eventManager.broadcast({ name: 'primeCollabListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCollaborateurById(index: number, item: Collaborateur) {
        return item.id;
    }

    trackPrimeById(index: number, item: Prime) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-prime-collab-popup',
    template: ''
})
export class PrimeCollabPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private primeCollabPopupService: PrimeCollabPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.primeCollabPopupService
                    .open(PrimeCollabDialogComponent as Component, params['id']);
            } else {
                this.primeCollabPopupService
                    .open(PrimeCollabDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
