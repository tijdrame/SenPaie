import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { StatutDemande } from './statut-demande.model';
import { StatutDemandePopupService } from './statut-demande-popup.service';
import { StatutDemandeService } from './statut-demande.service';

@Component({
    selector: 'jhi-statut-demande-delete-dialog',
    templateUrl: './statut-demande-delete-dialog.component.html'
})
export class StatutDemandeDeleteDialogComponent {

    statutDemande: StatutDemande;

    constructor(
        private statutDemandeService: StatutDemandeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.statutDemandeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statutDemandeListModification',
                content: 'Deleted an statutDemande'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-statut-demande-delete-popup',
    template: ''
})
export class StatutDemandeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statutDemandePopupService: StatutDemandePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.statutDemandePopupService
                .open(StatutDemandeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
