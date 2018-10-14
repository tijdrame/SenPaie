import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Regime } from './regime.model';
import { RegimePopupService } from './regime-popup.service';
import { RegimeService } from './regime.service';

@Component({
    selector: 'jhi-regime-delete-dialog',
    templateUrl: './regime-delete-dialog.component.html'
})
export class RegimeDeleteDialogComponent {

    regime: Regime;

    constructor(
        private regimeService: RegimeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regimeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'regimeListModification',
                content: 'Deleted an regime'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-regime-delete-popup',
    template: ''
})
export class RegimeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regimePopupService: RegimePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.regimePopupService
                .open(RegimeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
