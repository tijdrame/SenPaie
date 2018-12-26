import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Recap } from './recap.model';
import { RecapPopupService } from './recap-popup.service';
import { RecapService } from './recap.service';

@Component({
    selector: 'jhi-recap-delete-dialog',
    templateUrl: './recap-delete-dialog.component.html'
})
export class RecapDeleteDialogComponent {

    recap: Recap;

    constructor(
        private recapService: RecapService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.recapService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'recapListModification',
                content: 'Deleted an recap'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-recap-delete-popup',
    template: ''
})
export class RecapDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private recapPopupService: RecapPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.recapPopupService
                .open(RecapDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
