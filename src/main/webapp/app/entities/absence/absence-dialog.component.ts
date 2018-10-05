import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Absence } from './absence.model';
import { AbsencePopupService } from './absence-popup.service';
import { AbsenceService } from './absence.service';
import { User, UserService } from '../../shared';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { Motif, MotifService } from '../motif';
import { Exercice, ExerciceService } from '../exercice';

@Component({
    selector: 'jhi-absence-dialog',
    templateUrl: './absence-dialog.component.html'
})
export class AbsenceDialogComponent implements OnInit {

    absence: Absence;
    isSaving: boolean;

    users: User[];

    collaborateurs: Collaborateur[];

    motifs: Motif[];

    exercices: Exercice[];
    dateAbsenceDp: any;
    dateCreatedDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private absenceService: AbsenceService,
        private userService: UserService,
        private collaborateurService: CollaborateurService,
        private motifService: MotifService,
        private exerciceService: ExerciceService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.motifService.query()
            .subscribe((res: HttpResponse<Motif[]>) => { this.motifs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.exerciceService.query()
            .subscribe((res: HttpResponse<Exercice[]>) => { this.exercices = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.absence.id !== undefined) {
            this.subscribeToSaveResponse(
                this.absenceService.update(this.absence));
        } else {
            this.subscribeToSaveResponse(
                this.absenceService.create(this.absence));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Absence>>) {
        result.subscribe((res: HttpResponse<Absence>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Absence) {
        this.eventManager.broadcast({ name: 'absenceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCollaborateurById(index: number, item: Collaborateur) {
        return item.id;
    }

    trackMotifById(index: number, item: Motif) {
        return item.id;
    }

    trackExerciceById(index: number, item: Exercice) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-absence-popup',
    template: ''
})
export class AbsencePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private absencePopupService: AbsencePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.absencePopupService
                    .open(AbsenceDialogComponent as Component, params['id']);
            } else {
                this.absencePopupService
                    .open(AbsenceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
