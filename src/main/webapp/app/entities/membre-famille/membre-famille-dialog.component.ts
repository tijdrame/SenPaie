import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MembreFamille } from './membre-famille.model';
import { MembreFamillePopupService } from './membre-famille-popup.service';
import { MembreFamilleService } from './membre-famille.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { User, UserService } from '../../shared';
import { TypeRelation, TypeRelationService } from '../type-relation';
import {Sexe, SexeService} from "../sexe";
import {TypeContrat} from "../type-contrat";

@Component({
    selector: 'jhi-membre-famille-dialog',
    templateUrl: './membre-famille-dialog.component.html'
})
export class MembreFamilleDialogComponent implements OnInit {

    membreFamille: MembreFamille;
    isSaving: boolean;

    collaborateurs: Collaborateur[];

    users: User[];
    sexes: Sexe[];

    typerelations: TypeRelation[];
    dateNaissanceDp: any;
    dateMariageDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private membreFamilleService: MembreFamilleService,
        private collaborateurService: CollaborateurService,
        private userService: UserService,
        private sexeService: SexeService,
        private typeRelationService: TypeRelationService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.queryBis()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.sexeService.query()
            .subscribe((res: HttpResponse<Sexe[]>) => { this.sexes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.typeRelationService.query()
            .subscribe((res: HttpResponse<TypeRelation[]>) => { this.typerelations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.membreFamille, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.membreFamille.id !== undefined) {
            this.subscribeToSaveResponse(
                this.membreFamilleService.update(this.membreFamille));
        } else {
            this.subscribeToSaveResponse(
                this.membreFamilleService.create(this.membreFamille));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MembreFamille>>) {
        result.subscribe((res: HttpResponse<MembreFamille>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MembreFamille) {
        this.eventManager.broadcast({ name: 'membreFamilleListModification', content: 'OK'});
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
    trackTypeContratById(index: number, item: TypeContrat) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackTypeRelationById(index: number, item: TypeRelation) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-membre-famille-popup',
    template: ''
})
export class MembreFamillePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private membreFamillePopupService: MembreFamillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.membreFamillePopupService
                    .open(MembreFamilleDialogComponent as Component, params['id']);
            } else {
                this.membreFamillePopupService
                    .open(MembreFamilleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
