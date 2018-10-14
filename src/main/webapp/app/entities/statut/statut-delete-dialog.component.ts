import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Statut } from './statut.model';
import { StatutPopupService } from './statut-popup.service';
import { StatutService } from './statut.service';

@Component({
    selector: 'jhi-statut-delete-dialog',
    templateUrl: './statut-delete-dialog.component.html'
})
export class StatutDeleteDialogComponent {

    statut: Statut;

    constructor(
        private statutService: StatutService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.statutService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statutListModification',
                content: 'Deleted an statut'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-statut-delete-popup',
    template: ''
})
export class StatutDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statutPopupService: StatutPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.statutPopupService
                .open(StatutDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
