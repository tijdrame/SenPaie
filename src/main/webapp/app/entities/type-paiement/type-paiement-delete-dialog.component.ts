import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypePaiement } from './type-paiement.model';
import { TypePaiementPopupService } from './type-paiement-popup.service';
import { TypePaiementService } from './type-paiement.service';

@Component({
    selector: 'jhi-type-paiement-delete-dialog',
    templateUrl: './type-paiement-delete-dialog.component.html'
})
export class TypePaiementDeleteDialogComponent {

    typePaiement: TypePaiement;

    constructor(
        private typePaiementService: TypePaiementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typePaiementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typePaiementListModification',
                content: 'Deleted an typePaiement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-paiement-delete-popup',
    template: ''
})
export class TypePaiementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typePaiementPopupService: TypePaiementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typePaiementPopupService
                .open(TypePaiementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
