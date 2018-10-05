import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Structure } from './structure.model';
import { StructureService } from './structure.service';

@Component({
    selector: 'jhi-structure-detail',
    templateUrl: './structure-detail.component.html'
})
export class StructureDetailComponent implements OnInit, OnDestroy {

    structure: Structure;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private structureService: StructureService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStructures();
    }

    load(id) {
        this.structureService.find(id)
            .subscribe((structureResponse: HttpResponse<Structure>) => {
                this.structure = structureResponse.body;
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

    registerChangeInStructures() {
        this.eventSubscriber = this.eventManager.subscribe(
            'structureListModification',
            (response) => this.load(this.structure.id)
        );
    }
}
