import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinPopupService } from './bulletin-popup.service';
import { BulletinService } from './bulletin.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { TypePaiement, TypePaiementService } from '../type-paiement';
import { User, UserService } from '../../shared';
import { Remboursement, RemboursementService } from '../remboursement';

@Component({
    selector: 'jhi-bulletin-dialog',
    templateUrl: './bulletin-dialog.component.html'
})
export class BulletinDialogComponent implements OnInit {

    bulletin: Bulletin;
    isSaving: boolean;

    collaborateurs: Collaborateur[];

    typepaiements: TypePaiement[];

    users: User[];

    remboursements: Remboursement[];
    dateCreatedDp: any;
    dateUpdatedDp: any;
    dateDeletedDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bulletinService: BulletinService,
        private collaborateurService: CollaborateurService,
        private typePaiementService: TypePaiementService,
        private userService: UserService,
        private remboursementService: RemboursementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.typePaiementService.query()
            .subscribe((res: HttpResponse<TypePaiement[]>) => { this.typepaiements = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.remboursementService.query()
            .subscribe((res: HttpResponse<Remboursement[]>) => { this.remboursements = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bulletin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bulletinService.update(this.bulletin));
        } else {
            this.subscribeToSaveResponse(
                this.bulletinService.create(this.bulletin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bulletin>>) {
        result.subscribe((res: HttpResponse<Bulletin>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bulletin) {
        this.eventManager.broadcast({ name: 'bulletinListModification', content: 'OK'});
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

    trackTypePaiementById(index: number, item: TypePaiement) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackRemboursementById(index: number, item: Remboursement) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-bulletin-popup',
    template: ''
})
export class BulletinPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bulletinPopupService: BulletinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bulletinPopupService
                    .open(BulletinDialogComponent as Component, params['id']);
            } else {
                this.bulletinPopupService
                    .open(BulletinDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
