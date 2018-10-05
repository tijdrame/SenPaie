import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bareme } from './bareme.model';
import { BaremePopupService } from './bareme-popup.service';
import { BaremeService } from './bareme.service';

@Component({
    selector: 'jhi-bareme-delete-dialog',
    templateUrl: './bareme-delete-dialog.component.html'
})
export class BaremeDeleteDialogComponent {

    bareme: Bareme;

    constructor(
        private baremeService: BaremeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.baremeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'baremeListModification',
                content: 'Deleted an bareme'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bareme-delete-popup',
    template: ''
})
export class BaremeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private baremePopupService: BaremePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.baremePopupService
                .open(BaremeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
