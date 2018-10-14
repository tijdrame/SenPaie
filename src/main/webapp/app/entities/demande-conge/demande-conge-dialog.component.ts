import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DemandeConge } from './demande-conge.model';
import { DemandeCongePopupService } from './demande-conge-popup.service';
import { DemandeCongeService } from './demande-conge.service';
import { StatutDemande, StatutDemandeService } from '../statut-demande';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { User, UserService } from '../../shared';
import {TypeAbsence, TypeAbsenceService} from "../type-absence";
import {Categorie} from "../categorie";

@Component({
    selector: 'jhi-demande-conge-dialog',
    templateUrl: './demande-conge-dialog.component.html'
})
export class DemandeCongeDialogComponent implements OnInit {

    demandeConge: DemandeConge;
    isSaving: boolean;

    statutdemandes: StatutDemande[];

    collaborateurs: Collaborateur[];

    users: User[];
    dateCreatedDp: any;
    dateDebutDp: any;
    dateFinDp: any;
    typeAbsences: TypeAbsence[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private demandeCongeService: DemandeCongeService,
        private statutDemandeService: StatutDemandeService,
        private collaborateurService: CollaborateurService,
        private typeAbsenceService: TypeAbsenceService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.statutDemandeService.query()
            .subscribe((res: HttpResponse<StatutDemande[]>) => { this.statutdemandes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.typeAbsenceService.query()
            .subscribe((res: HttpResponse<TypeAbsence[]>) => { this.typeAbsences = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.demandeConge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.demandeCongeService.update(this.demandeConge));
        } else {
            this.subscribeToSaveResponse(
                this.demandeCongeService.create(this.demandeConge));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DemandeConge>>) {
        result.subscribe((res: HttpResponse<DemandeConge>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DemandeConge) {
        this.eventManager.broadcast({ name: 'demandeCongeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackStatutDemandeById(index: number, item: StatutDemande) {
        return item.id;
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.id;
    }

    trackCollaborateurById(index: number, item: Collaborateur) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-demande-conge-popup',
    template: ''
})
export class DemandeCongePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private demandeCongePopupService: DemandeCongePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.demandeCongePopupService
                    .open(DemandeCongeDialogComponent as Component, params['id']);
            } else {
                this.demandeCongePopupService
                    .open(DemandeCongeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
