import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Categorie } from './categorie.model';
import { CategoriePopupService } from './categorie-popup.service';
import { CategorieService } from './categorie.service';

@Component({
    selector: 'jhi-categorie-dialog',
    templateUrl: './categorie-dialog.component.html'
})
export class CategorieDialogComponent implements OnInit {

    categorie: Categorie;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private categorieService: CategorieService,
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
        if (this.categorie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.categorieService.update(this.categorie));
        } else {
            this.subscribeToSaveResponse(
                this.categorieService.create(this.categorie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Categorie>>) {
        result.subscribe((res: HttpResponse<Categorie>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Categorie) {
        this.eventManager.broadcast({ name: 'categorieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-categorie-popup',
    template: ''
})
export class CategoriePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categoriePopupService: CategoriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.categoriePopupService
                    .open(CategorieDialogComponent as Component, params['id']);
            } else {
                this.categoriePopupService
                    .open(CategorieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
