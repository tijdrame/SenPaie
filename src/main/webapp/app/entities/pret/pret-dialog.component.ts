import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pret } from './pret.model';
import { PretPopupService } from './pret-popup.service';
import { PretService } from './pret.service';
import { User, UserService } from '../../shared';

@Component({
    selector: 'jhi-pret-dialog',
    templateUrl: './pret-dialog.component.html'
})
export class PretDialogComponent implements OnInit {

    pret: Pret;
    isSaving: boolean;

    users: User[];
    datePretDp: any;
    dateDebutRemboursementDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pretService: PretService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pret.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pretService.update(this.pret));
        } else {
            this.subscribeToSaveResponse(
                this.pretService.create(this.pret));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pret>>) {
        result.subscribe((res: HttpResponse<Pret>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Pret) {
        this.eventManager.broadcast({ name: 'pretListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-pret-popup',
    template: ''
})
export class PretPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pretPopupService: PretPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pretPopupService
                    .open(PretDialogComponent as Component, params['id']);
            } else {
                this.pretPopupService
                    .open(PretDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
