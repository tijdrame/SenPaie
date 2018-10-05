import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DetailPret } from './detail-pret.model';
import { DetailPretPopupService } from './detail-pret-popup.service';
import { DetailPretService } from './detail-pret.service';

@Component({
    selector: 'jhi-detail-pret-delete-dialog',
    templateUrl: './detail-pret-delete-dialog.component.html'
})
export class DetailPretDeleteDialogComponent {

    detailPret: DetailPret;

    constructor(
        private detailPretService: DetailPretService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detailPretService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'detailPretListModification',
                content: 'Deleted an detailPret'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detail-pret-delete-popup',
    template: ''
})
export class DetailPretDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private detailPretPopupService: DetailPretPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.detailPretPopupService
                .open(DetailPretDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
