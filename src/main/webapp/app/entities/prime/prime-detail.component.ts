import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Prime } from './prime.model';
import { PrimeService } from './prime.service';

@Component({
    selector: 'jhi-prime-detail',
    templateUrl: './prime-detail.component.html'
})
export class PrimeDetailComponent implements OnInit, OnDestroy {

    prime: Prime;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private primeService: PrimeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPrimes();
    }

    load(id) {
        this.primeService.find(id)
            .subscribe((primeResponse: HttpResponse<Prime>) => {
                this.prime = primeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPrimes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'primeListModification',
            (response) => this.load(this.prime.id)
        );
    }
}
