import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DemandeConge } from './demande-conge.model';
import { DemandeCongePopupService } from './demande-conge-popup.service';
import { DemandeCongeService } from './demande-conge.service';

@Component({
    selector: 'jhi-demande-conge-delete-dialog',
    templateUrl: './demande-conge-delete-dialog.component.html'
})
export class DemandeCongeDeleteDialogComponent {

    demandeConge: DemandeConge;

    constructor(
        private demandeCongeService: DemandeCongeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.demandeCongeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'demandeCongeListModification',
                content: 'Deleted an demandeConge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-demande-conge-delete-popup',
    template: ''
})
export class DemandeCongeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private demandeCongePopupService: DemandeCongePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.demandeCongePopupService
                .open(DemandeCongeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
