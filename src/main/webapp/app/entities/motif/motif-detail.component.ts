import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Motif } from './motif.model';
import { MotifService } from './motif.service';

@Component({
    selector: 'jhi-motif-detail',
    templateUrl: './motif-detail.component.html'
})
export class MotifDetailComponent implements OnInit, OnDestroy {

    motif: Motif;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private motifService: MotifService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMotifs();
    }

    load(id) {
        this.motifService.find(id)
            .subscribe((motifResponse: HttpResponse<Motif>) => {
                this.motif = motifResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMotifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'motifListModification',
            (response) => this.load(this.motif.id)
        );
    }
}
