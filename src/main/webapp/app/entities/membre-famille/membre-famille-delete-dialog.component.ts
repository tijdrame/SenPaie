import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MembreFamille } from './membre-famille.model';
import { MembreFamillePopupService } from './membre-famille-popup.service';
import { MembreFamilleService } from './membre-famille.service';

@Component({
    selector: 'jhi-membre-famille-delete-dialog',
    templateUrl: './membre-famille-delete-dialog.component.html'
})
export class MembreFamilleDeleteDialogComponent {

    membreFamille: MembreFamille;

    constructor(
        private membreFamilleService: MembreFamilleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.membreFamilleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'membreFamilleListModification',
                content: 'Deleted an membreFamille'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-membre-famille-delete-popup',
    template: ''
})
export class MembreFamilleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private membreFamillePopupService: MembreFamillePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.membreFamillePopupService
                .open(MembreFamilleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
