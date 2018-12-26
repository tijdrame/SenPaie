import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AvantageCollab } from './avantage-collab.model';
import { AvantageCollabPopupService } from './avantage-collab-popup.service';
import { AvantageCollabService } from './avantage-collab.service';

@Component({
    selector: 'jhi-avantage-collab-delete-dialog',
    templateUrl: './avantage-collab-delete-dialog.component.html'
})
export class AvantageCollabDeleteDialogComponent {

    avantageCollab: AvantageCollab;

    constructor(
        private avantageCollabService: AvantageCollabService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.avantageCollabService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'avantageCollabListModification',
                content: 'Deleted an avantageCollab'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-avantage-collab-delete-popup',
    template: ''
})
export class AvantageCollabDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private avantageCollabPopupService: AvantageCollabPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.avantageCollabPopupService
                .open(AvantageCollabDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
