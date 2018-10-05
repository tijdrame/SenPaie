import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Remboursement } from './remboursement.model';
import { RemboursementPopupService } from './remboursement-popup.service';
import { RemboursementService } from './remboursement.service';

@Component({
    selector: 'jhi-remboursement-delete-dialog',
    templateUrl: './remboursement-delete-dialog.component.html'
})
export class RemboursementDeleteDialogComponent {

    remboursement: Remboursement;

    constructor(
        private remboursementService: RemboursementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.remboursementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'remboursementListModification',
                content: 'Deleted an remboursement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-remboursement-delete-popup',
    template: ''
})
export class RemboursementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private remboursementPopupService: RemboursementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.remboursementPopupService
                .open(RemboursementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
