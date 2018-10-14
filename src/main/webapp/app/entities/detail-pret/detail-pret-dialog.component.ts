import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DetailPret } from './detail-pret.model';
import { DetailPretPopupService } from './detail-pret-popup.service';
import { DetailPretService } from './detail-pret.service';
import { Collaborateur, CollaborateurService } from '../collaborateur';
import { Pret, PretService } from '../pret';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-detail-pret-dialog',
    templateUrl: './detail-pret-dialog.component.html'
})
export class DetailPretDialogComponent implements OnInit {

    detailPret: DetailPret;
    isSaving: boolean;

    collaborateurs: Collaborateur[];

    prets: Pret[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private detailPretService: DetailPretService,
        private collaborateurService: CollaborateurService,
        private pretService: PretService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.collaborateurService.query()
            .subscribe((res: HttpResponse<Collaborateur[]>) => { this.collaborateurs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.pretService.query()
            .subscribe((res: HttpResponse<Pret[]>) => { this.prets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.detailPret.id !== undefined) {
            this.subscribeToSaveResponse(
                this.detailPretService.update(this.detailPret));
        } else {
            this.subscribeToSaveResponse(
                this.detailPretService.create(this.detailPret));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DetailPret>>) {
        result.subscribe((res: HttpResponse<DetailPret>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DetailPret) {
        this.eventManager.broadcast({ name: 'detailPretListModification', content: 'OK'});
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

    trackPretById(index: number, item: Pret) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-detail-pret-popup',
    template: ''
})
export class DetailPretPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailPretPopupService: DetailPretPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.detailPretPopupService
                    .open(DetailPretDialogComponent as Component, params['id']);
            } else {
                this.detailPretPopupService
                    .open(DetailPretDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
