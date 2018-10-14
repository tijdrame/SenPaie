import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Collaborateur } from './collaborateur.model';
import { CollaborateurPopupService } from './collaborateur-popup.service';
import { CollaborateurService } from './collaborateur.service';
import { Fonction, FonctionService } from '../fonction';
import { Categorie, CategorieService } from '../categorie';
import { Nationalite, NationaliteService } from '../nationalite';
import { Statut, StatutService } from '../statut';
import { SituationMatrimoniale, SituationMatrimonialeService } from '../situation-matrimoniale';
import { TypeContrat, TypeContratService } from '../type-contrat';
import { User, UserService } from '../../shared';
import {Regime, RegimeService} from "../regime";
import {Sexe, SexeService} from "../sexe";

@Component({
    selector: 'jhi-collaborateur-dialog',
    templateUrl: './collaborateur-dialog.component.html'
})
export class CollaborateurDialogComponent implements OnInit {

    collaborateur: Collaborateur;
    isSaving: boolean;

    fonctions: Fonction[];

    categories: Categorie[];

    nationalites: Nationalite[];

    statuts: Statut[];

    situationmatrimoniales: SituationMatrimoniale[];

    typecontrats: TypeContrat[];

    regimes: Regime[];
    sexes: Sexe[];

    users: User[];
    dateNaissanceDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private collaborateurService: CollaborateurService,
        private fonctionService: FonctionService,
        private categorieService: CategorieService,
        private nationaliteService: NationaliteService,
        private statutService: StatutService,
        private situationMatrimonialeService: SituationMatrimonialeService,
        private typeContratService: TypeContratService,
        private regimeService: RegimeService,
        private sexeService: SexeService,
        private userService: UserService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.fonctionService.query()
            .subscribe((res: HttpResponse<Fonction[]>) => { this.fonctions = res.body; },
                (res: HttpErrorResponse) => this.onError(res.message));
        this.categorieService.query()
            .subscribe((res: HttpResponse<Categorie[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.nationaliteService.queryBis()
            .subscribe((res: HttpResponse<Nationalite[]>) => { this.nationalites = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.statutService.query()
            .subscribe((res: HttpResponse<Statut[]>) => { this.statuts = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.situationMatrimonialeService.query()
            .subscribe((res: HttpResponse<SituationMatrimoniale[]>) => { this.situationmatrimoniales = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.typeContratService.query()
            .subscribe((res: HttpResponse<TypeContrat[]>) => { this.typecontrats = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.regimeService.query()
            .subscribe((res: HttpResponse<Regime[]>) => { this.regimes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.sexeService.query()
            .subscribe((res: HttpResponse<Sexe[]>) => { this.sexes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.collaborateur, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.collaborateur.id !== undefined) {
            this.subscribeToSaveResponse(
                this.collaborateurService.update(this.collaborateur));
        } else {
            this.subscribeToSaveResponse(
                this.collaborateurService.create(this.collaborateur));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Collaborateur>>) {
        result.subscribe((res: HttpResponse<Collaborateur>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Collaborateur) {
        this.eventManager.broadcast({ name: 'collaborateurListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFonctionById(index: number, item: Fonction) {
        return item.id;
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.id;
    }

    trackNationaliteById(index: number, item: Nationalite) {
        return item.id;
    }

    trackStatutById(index: number, item: Statut) {
        return item.id;
    }

    trackSituationMatrimonialeById(index: number, item: SituationMatrimoniale) {
        return item.id;
    }

    trackTypeContratById(index: number, item: TypeContrat) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-collaborateur-popup',
    template: ''
})
export class CollaborateurPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private collaborateurPopupService: CollaborateurPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.collaborateurPopupService
                    .open(CollaborateurDialogComponent as Component, params['id']);
            } else {
                this.collaborateurPopupService
                    .open(CollaborateurDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
