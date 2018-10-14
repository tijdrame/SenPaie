import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { FonctionComponent } from './fonction.component';
import { FonctionDetailComponent } from './fonction-detail.component';
import { FonctionPopupComponent } from './fonction-dialog.component';
import { FonctionDeletePopupComponent } from './fonction-delete-dialog.component';

@Injectable()
export class FonctionResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const fonctionRoute: Routes = [
    {
        path: 'fonction',
        component: FonctionComponent,
        resolve: {
            'pagingParams': FonctionResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'fonction/:id',
        component: FonctionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fonctionPopupRoute: Routes = [
    {
        path: 'fonction-new',
        component: FonctionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fonction/:id/edit',
        component: FonctionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'fonction/:id/delete',
        component: FonctionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.fonction.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
