import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AvantageCollab } from './avantage-collab.model';
import { AvantageCollabPopupService } from './avantage-collab-popup.service';
import { AvantageCollabService } from './avantage-collab.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { Avantage, AvantageService } from '../avantage';

@Component({
    selector: 'jhi-avantage-collab-dialog',
    templateUrl: './avantage-collab-dialog.component.html'
})
export class AvantageCollabDialogComponent implements OnInit {

    avantageCollab: AvantageCollab;
    isSaving: boolean;

    collaborateurs: Collaborateur[];

    avantages: Avantage[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private avantageCollabService: AvantageCollabService,
        private collaborateurService: CollaborateurService,
        private avantageService: AvantageService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.avantageService.query()
            .subscribe((res: HttpResponse<Avantage[]>) => { this.avantages = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.avantageCollab.id !== undefined) {
            this.subscribeToSaveResponse(
                this.avantageCollabService.update(this.avantageCollab));
        } else {
            this.subscribeToSaveResponse(
                this.avantageCollabService.create(this.avantageCollab));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AvantageCollab>>) {
        result.subscribe((res: HttpResponse<AvantageCollab>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AvantageCollab) {
        this.eventManager.broadcast({ name: 'avantageCollabListModification', content: 'OK'});
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

    trackAvantageById(index: number, item: Avantage) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-avantage-collab-popup',
    template: ''
})
export class AvantageCollabPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avantageCollabPopupService: AvantageCollabPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.avantageCollabPopupService
                    .open(AvantageCollabDialogComponent as Component, params['id']);
            } else {
                this.avantageCollabPopupService
                    .open(AvantageCollabDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
