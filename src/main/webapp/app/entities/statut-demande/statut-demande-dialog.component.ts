import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StatutDemande } from './statut-demande.model';
import { StatutDemandePopupService } from './statut-demande-popup.service';
import { StatutDemandeService } from './statut-demande.service';

@Component({
    selector: 'jhi-statut-demande-dialog',
    templateUrl: './statut-demande-dialog.component.html'
})
export class StatutDemandeDialogComponent implements OnInit {

    statutDemande: StatutDemande;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private statutDemandeService: StatutDemandeService,
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
        if (this.statutDemande.id !== undefined) {
            this.subscribeToSaveResponse(
                this.statutDemandeService.update(this.statutDemande));
        } else {
            this.subscribeToSaveResponse(
                this.statutDemandeService.create(this.statutDemande));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<StatutDemande>>) {
        result.subscribe((res: HttpResponse<StatutDemande>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: StatutDemande) {
        this.eventManager.broadcast({ name: 'statutDemandeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-statut-demande-popup',
    template: ''
})
export class StatutDemandePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statutDemandePopupService: StatutDemandePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.statutDemandePopupService
                    .open(StatutDemandeDialogComponent as Component, params['id']);
            } else {
                this.statutDemandePopupService
                    .open(StatutDemandeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
