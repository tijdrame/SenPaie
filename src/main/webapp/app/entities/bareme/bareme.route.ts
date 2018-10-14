import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BaremeComponent } from './bareme.component';
import { BaremeDetailComponent } from './bareme-detail.component';
import { BaremePopupComponent } from './bareme-dialog.component';
import { BaremeDeletePopupComponent } from './bareme-delete-dialog.component';

@Injectable()
export class BaremeResolvePagingParams implements Resolve<any> {

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

export const baremeRoute: Routes = [
    {
        path: 'bareme',
        component: BaremeComponent,
        resolve: {
            'pagingParams': BaremeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bareme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bareme/:id',
        component: BaremeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bareme.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const baremePopupRoute: Routes = [
    {
        path: 'bareme-new',
        component: BaremePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bareme/:id/edit',
        component: BaremePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bareme/:id/delete',
        component: BaremeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.bareme.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
