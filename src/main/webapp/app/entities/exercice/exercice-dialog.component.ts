import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Exercice } from './exercice.model';
import { ExercicePopupService } from './exercice-popup.service';
import { ExerciceService } from './exercice.service';

@Component({
    selector: 'jhi-exercice-dialog',
    templateUrl: './exercice-dialog.component.html'
})
export class ExerciceDialogComponent implements OnInit {

    exercice: Exercice;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private exerciceService: ExerciceService,
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
        this.exercice.finExercice = this.exercice.debutExercice + 1;
        if (this.exercice.id !== undefined) {
            this.subscribeToSaveResponse(
                this.exerciceService.update(this.exercice));
        } else {
            this.subscribeToSaveResponse(
                this.exerciceService.create(this.exercice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Exercice>>) {
        result.subscribe((res: HttpResponse<Exercice>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Exercice) {
        this.eventManager.broadcast({ name: 'exerciceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-exercice-popup',
    template: ''
})
export class ExercicePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private exercicePopupService: ExercicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.exercicePopupService
                    .open(ExerciceDialogComponent as Component, params['id']);
            } else {
                this.exercicePopupService
                    .open(ExerciceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
