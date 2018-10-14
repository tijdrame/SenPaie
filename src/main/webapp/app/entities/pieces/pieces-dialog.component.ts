import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Pieces } from './pieces.model';
import { PiecesPopupService } from './pieces-popup.service';
import { PiecesService } from './pieces.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-pieces-dialog',
    templateUrl: './pieces-dialog.component.html'
})
export class PiecesDialogComponent implements OnInit {

    pieces: Pieces;
    isSaving: boolean;

    collaborateurs: Collaborateur[];

    users: User[];
    dateDebutDp: any;
    dateExpirationDp: any;
    dateCreatedDp: any;
    dateDeletedDp: any;
    dateUpdatedDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private piecesService: PiecesService,
        private collaborateurService: CollaborateurService,
        private userService: UserService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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
        this.dataUtils.clearInputImage(this.pieces, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pieces.id !== undefined) {
            this.subscribeToSaveResponse(
                this.piecesService.update(this.pieces));
        } else {
            this.subscribeToSaveResponse(
                this.piecesService.create(this.pieces));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pieces>>) {
        result.subscribe((res: HttpResponse<Pieces>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Pieces) {
        this.eventManager.broadcast({ name: 'piecesListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pieces-popup',
    template: ''
})
export class PiecesPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private piecesPopupService: PiecesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.piecesPopupService
                    .open(PiecesDialogComponent as Component, params['id']);
            } else {
                this.piecesPopupService
                    .open(PiecesDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
