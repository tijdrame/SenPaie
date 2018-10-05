import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Nationalite } from './nationalite.model';
import { NationalitePopupService } from './nationalite-popup.service';
import { NationaliteService } from './nationalite.service';

@Component({
    selector: 'jhi-nationalite-delete-dialog',
    templateUrl: './nationalite-delete-dialog.component.html'
})
export class NationaliteDeleteDialogComponent {

    nationalite: Nationalite;

    constructor(
        private nationaliteService: NationaliteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nationaliteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nationaliteListModification',
                content: 'Deleted an nationalite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-nationalite-delete-popup',
    template: ''
})
export class NationaliteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nationalitePopupService: NationalitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nationalitePopupService
                .open(NationaliteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
