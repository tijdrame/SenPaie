import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bulletin } from './bulletin.model';
import { BulletinPopupService } from './bulletin-popup.service';
import { BulletinService } from './bulletin.service';

@Component({
    selector: 'jhi-bulletin-delete-dialog',
    templateUrl: './bulletin-delete-dialog.component.html'
})
export class BulletinDeleteDialogComponent {

    bulletin: Bulletin;

    constructor(
        private bulletinService: BulletinService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bulletinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bulletinListModification',
                content: 'Deleted an bulletin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bulletin-delete-popup',
    template: ''
})
export class BulletinDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bulletinPopupService: BulletinPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bulletinPopupService
                .open(BulletinDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
