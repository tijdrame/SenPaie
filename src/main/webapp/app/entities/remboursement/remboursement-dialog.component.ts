import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Remboursement } from './remboursement.model';
import { RemboursementPopupService } from './remboursement-popup.service';
import { RemboursementService } from './remboursement.service';
import { DetailPret, DetailPretService } from '../detail-pret';
import { User, UserService } from '../../shared';
import { Bulletin, BulletinService } from '../bulletin';

@Component({
    selector: 'jhi-remboursement-dialog',
    templateUrl: './remboursement-dialog.component.html'
})
export class RemboursementDialogComponent implements OnInit {

    remboursement: Remboursement;
    isSaving: boolean;

    detailprets: DetailPret[];

    users: User[];

    bulletins: Bulletin[];
    dateRemboursementDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private remboursementService: RemboursementService,
        private detailPretService: DetailPretService,
        private userService: UserService,
        private bulletinService: BulletinService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.detailPretService.query()
            .subscribe((res: HttpResponse<DetailPret[]>) => { this.detailprets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.bulletinService.query()
            .subscribe((res: HttpResponse<Bulletin[]>) => { this.bulletins = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.remboursement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.remboursementService.update(this.remboursement));
        } else {
            this.subscribeToSaveResponse(
                this.remboursementService.create(this.remboursement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Remboursement>>) {
        result.subscribe((res: HttpResponse<Remboursement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Remboursement) {
        this.eventManager.broadcast({ name: 'remboursementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDetailPretById(index: number, item: DetailPret) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackBulletinById(index: number, item: Bulletin) {
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
    selector: 'jhi-remboursement-popup',
    template: ''
})
export class RemboursementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private remboursementPopupService: RemboursementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.remboursementPopupService
                    .open(RemboursementDialogComponent as Component, params['id']);
            } else {
                this.remboursementPopupService
                    .open(RemboursementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
