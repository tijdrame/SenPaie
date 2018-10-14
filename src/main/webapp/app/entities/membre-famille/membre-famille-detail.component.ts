import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { MembreFamille } from './membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';

@Component({
    selector: 'jhi-membre-famille-detail',
    templateUrl: './membre-famille-detail.component.html'
})
export class MembreFamilleDetailComponent implements OnInit, OnDestroy {

    membreFamille: MembreFamille;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private membreFamilleService: MembreFamilleService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMembreFamilles();
    }

    load(id) {
        this.membreFamilleService.find(id)
            .subscribe((membreFamilleResponse: HttpResponse<MembreFamille>) => {
                this.membreFamille = membreFamilleResponse.body;
            });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMembreFamilles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'membreFamilleListModification',
            (response) => this.load(this.membreFamille.id)
        );
    }
}
