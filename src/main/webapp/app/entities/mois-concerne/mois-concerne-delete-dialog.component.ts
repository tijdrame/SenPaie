import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MoisConcerne } from './mois-concerne.model';
import { MoisConcernePopupService } from './mois-concerne-popup.service';
import { MoisConcerneService } from './mois-concerne.service';

@Component({
    selector: 'jhi-mois-concerne-delete-dialog',
    templateUrl: './mois-concerne-delete-dialog.component.html'
})
export class MoisConcerneDeleteDialogComponent {

    moisConcerne: MoisConcerne;

    constructor(
        private moisConcerneService: MoisConcerneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moisConcerneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'moisConcerneListModification',
                content: 'Deleted an moisConcerne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mois-concerne-delete-popup',
    template: ''
})
export class MoisConcerneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moisConcernePopupService: MoisConcernePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.moisConcernePopupService
                .open(MoisConcerneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
