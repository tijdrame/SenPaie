import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Avantage } from './avantage.model';
import { AvantagePopupService } from './avantage-popup.service';
import { AvantageService } from './avantage.service';

@Component({
    selector: 'jhi-avantage-delete-dialog',
    templateUrl: './avantage-delete-dialog.component.html'
})
export class AvantageDeleteDialogComponent {

    avantage: Avantage;

    constructor(
        private avantageService: AvantageService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.avantageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'avantageListModification',
                content: 'Deleted an avantage'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-avantage-delete-popup',
    template: ''
})
export class AvantageDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avantagePopupService: AvantagePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.avantagePopupService
                .open(AvantageDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
